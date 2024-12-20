import os
import re

entities = [(i.replace('.kt', ''), "entities/" + i) for i in os.listdir("entities")]
base_entities = []
ign = []

def wr(file, text):
    with open(file, "w") as f:
        f.write(text)

for n, fname in entities:
    txt = open(fname).read()
    if '@Entity' not in txt:
        ign.append(n)
    if 'IBaseEntity' in txt:
        base_entities.append(n)


def proc_mapper():
    for n in os.listdir('mapper'):
        fn = 'mapper/'+n
        n = n.replace('.kt', '')
        txt = open(fn).read()
        if 'IBaseMapper' in txt: continue
        if n.replace("Mapper", "") not in base_entities: continue
        out = re.sub("(import org\.mapstruct\.\*)", "\\1\nimport ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper", txt)
        out = re.sub("abstract class (\w+)Mapper \{", "abstract class \\1Mapper : IBaseMapper<\\1, \\1Dto> {", out)
        out = out.replace("abstract fun", "abstract override fun")
        wr(fn, out)

def proc_dto():
    for n in os.listdir('dto'):
        fn = 'dto/'+n
        n = n.replace('.kt', '')
        txt = open(fn).read()
        if 'IBaseDto' in txt: continue
        if 'val id: Long? = null' not in txt: continue
        out = re.sub("data class", "import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto\n\ndata class", txt)
        out = out.strip() + " : IBaseDto"
        out = out.replace("val id: Long? = null", "override val id: Long? = null")
        wr(fn, out)


def proc_controller():
    for n, _ in entities:
        if n in ign: continue
        if n in base_entities:
            wr(f"controller/{n}Controller.kt", f"""
    package ru.bardinpetr.itmo.meddelivery.app.controller

    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController
    import ru.bardinpetr.itmo.meddelivery.app.dto.{n}Dto
    import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
    import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

    @RequestMapping("/api/{n.lower()}")
    @RestController
    class {n}Controller : AbstractCommonRestController<{n}, {n}Dto>({n}::class)
            """)
        else:
            wr(f"controller/{n}Controller.kt", f"""
    package ru.bardinpetr.itmo.meddelivery.app.controller

    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController
    import ru.bardinpetr.itmo.meddelivery.app.dto.{n}Dto
    import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
    import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

    @RequestMapping("/api/{n.lower()}")
    @RestController
    class {n}Controller {'{'}
    {'}'}
            """)

def proc_entities():
    for n, _ in entities:
        if n in ign: continue
        if n in base_entities:
            wr(f"repository/{n}Repository.kt", f"""
    package ru.bardinpetr.itmo.meddelivery.common.auth.repository

    import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
    import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

    interface {n}Repository : ICommonRestRepository<{n}>
            """)
        else:
            wr(f"repository/{n}Repository.kt", f"""
    package ru.bardinpetr.itmo.meddelivery.common.auth.repository

    import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
    import ru.bardinpetr.itmo.meddelivery.app.entities.{n}Id
    import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseCommonRestRepository

    interface {n}Repository : IBaseCommonRestRepository<{n}, {n}Id>
            """)

def gentext():
    out = []
    for n in os.listdir('dto'):
        txt = open('dto/'+n).read()
        txt = txt[txt.index("data class"):]
        txt = txt.replace(": IBaseDto", "")
        txt = txt.replace("override", "")
        out.append(txt)
    wr("DTO.kt", '\n'.join(out))

def proc_fe_services():
    imports = [
        f"import {'{'}{i}ControllerService{'}'} from \"../../lib\""
        for i,_ in entities
    ]
    services = [
        f"{i}: {i}ControllerService,"
        for i,_ in entities
    ]

    wr("fe.services.ts", """
    import {Injectable, Injector, Type} from '@angular/core';
    __imports

    @Injectable({
      providedIn: 'root'
    })
    export class ApiProviderService {

      private services: { [key: string]: Type<any> } = {
        __services
      }

      constructor(
        private injector: Injector
      ) {
      }

      getAPI(id: string): any {
        return this.injector.get(this.services[id])
      }
    }
"""
.replace("__imports", '\n'.join(imports))
.replace("__services", '\n'.join(services))
)


proc_fe_services()