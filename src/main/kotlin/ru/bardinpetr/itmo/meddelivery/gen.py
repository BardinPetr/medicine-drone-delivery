import os
import re

# entities = [(i.replace('.kt', ''), "entities/" + i) for i in os.listdir("entities")]
# base_entities = []
# ign = []

def wr(file, text):
    with open(file, "w") as f:
        f.write(text)

def check_entity(file_path):
    txt = open(file_path).read()
    return 'IBaseEntity' in txt and '@Entity' in txt

# def proc_mapper():
#     for n in os.listdir('mapper'):
#         fn = 'mapper/'+n
#         n = n.replace('.kt', '')
#         txt = open(fn).read()
#         if 'IBaseMapper' in txt: continue
#         if n.replace("Mapper", "") not in base_entities: continue
#         out = re.sub("(import org\.mapstruct\.\*)", "\\1\nimport ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper", txt)
#         out = re.sub("abstract class (\w+)Mapper \{", "abstract class \\1Mapper : IBaseMapper<\\1, \\1Dto> {", out)
#         out = out.replace("abstract fun", "abstract override fun")
#         wr(fn, out)
#
# def proc_dto():
#     for n in os.listdir('dto'):
#         fn = 'dto/'+n
#         n = n.replace('.kt', '')
#         txt = open(fn).read()
#         if 'IBaseDto' in txt: continue
#         if 'val id: Long? = null' not in txt: continue
#         out = txt.replace("data class", "import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto\n\ndata class")
#         out = out.strip() + " : IBaseDto"
#         out = out.replace("val id: Long? = null", "override val id: Long? = null")
#         wr(fn, out)
#
#
# def proc_controller():
#     for n, _ in entities:
#         if n in ign: continue
#         if n in base_entities:
#             wr(f"controller/{n}Controller.kt", f"""
#     package ru.bardinpetr.itmo.meddelivery.app.controller
#
#     import org.springframework.web.bind.annotation.RequestMapping
#     import org.springframework.web.bind.annotation.RestController
#     import ru.bardinpetr.itmo.meddelivery.app.dto.{n}Dto
#     import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
#     import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
#
#     @RequestMapping("/api/{n.lower()}")
#     @RestController
#     class {n}Controller : AbstractCommonRestController<{n}, {n}Dto>({n}::class)
#             """)
#         else:
#             wr(f"controller/{n}Controller.kt", f"""
#     package ru.bardinpetr.itmo.meddelivery.app.controller
#
#     import org.springframework.web.bind.annotation.RequestMapping
#     import org.springframework.web.bind.annotation.RestController
#     import ru.bardinpetr.itmo.meddelivery.app.dto.{n}Dto
#     import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
#     import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
#
#     @RequestMapping("/api/{n.lower()}")
#     @RestController
#     class {n}Controller {'{'}
#     {'}'}
#             """)
#
# def proc_entities():
#     for n, _ in entities:
#         if n in ign: continue
#         if n in base_entities:
#             wr(f"repository/{n}Repository.kt", f"""
#     package ru.bardinpetr.itmo.meddelivery.common.auth.repository
#
#     import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
#     import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository
#
#     interface {n}Repository : ICommonRestRepository<{n}>
#             """)
#         else:
#             wr(f"repository/{n}Repository.kt", f"""
#     package ru.bardinpetr.itmo.meddelivery.common.auth.repository
#
#     import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
#     import ru.bardinpetr.itmo.meddelivery.app.entities.{n}Id
#     import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseCommonRestRepository
#
#     interface {n}Repository : IBaseCommonRestRepository<{n}, {n}Id>
#             """)
#
# def gentext():
#     out = []
#     for n in os.listdir('dto'):
#         txt = open('dto/'+n).read()
#         txt = txt[txt.index("data class"):]
#         txt = txt.replace(": IBaseDto", "")
#         txt = txt.replace("override", "")
#         out.append(txt)
#     wr("DTO.kt", '\n'.join(out))

def scan_entities():
    base_dir = "app/entities"
    entities = []
    cwd = os.getcwd()
    for root, subdirs, files in os.walk(base_dir):
        e_path = root.removeprefix(base_dir).split('/')[1:]
        for i in files:
            path = os.path.join(cwd, root, i)
            if not check_entity(path): continue
            name = i.removesuffix(".kt")
            entities.append({
                "name": name,
                "fqn": ".".join([*e_path, name]),
                "path": path
            })
    for i in entities:
        print(i)
    return entities

def proc_service(entity):
    n = entity['name']
    wr(
        f"app/service/{n}Service.kt",
        f"""
package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.{entity['fqn']}
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class {n}Service : AbstractBaseService<{n}>({n}::class)
""")


for i in scan_entities():
    proc_service(i)

