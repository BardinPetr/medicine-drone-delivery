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


for n in os.listdir('mapper'):
    fn = 'mapper/'+n
    n = n.replace('.kt', '')
    txt = open(fn).read()
    if 'IBaseMapper' in txt: continue
    out = re.sub("(import org.mapstruct.*)", "\\1\nimport ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper", txt)
    out = re.sub("abstract class (\w+)Mapper \{", "abstract class \\1Mapper : IBaseMapper<\\1, \\1Dto> {", txt)

#     wr(fn, out)
    print(out)
    exit(0)


# for n, _ in entities:
#     if n in ign: continue
#     if n in base_entities:
#         wr(f"controller/{n}Controller.kt", f"""
# package ru.bardinpetr.itmo.meddelivery.app.controller
#
# import org.springframework.web.bind.annotation.RequestMapping
# import org.springframework.web.bind.annotation.RestController
# import ru.bardinpetr.itmo.meddelivery.app.dto.{n}Dto
# import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
# import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
#
# @RequestMapping("/api/{n.lower()}")
# @RestController
# class {n}Controller : AbstractCommonRestController<{n}, {n}Dto>({n}::class)
#         """)
#     else:
#         wr(f"controller/{n}Controller.kt", f"""
# package ru.bardinpetr.itmo.meddelivery.app.controller
#
# import org.springframework.web.bind.annotation.RequestMapping
# import org.springframework.web.bind.annotation.RestController
# import ru.bardinpetr.itmo.meddelivery.app.dto.{n}Dto
# import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
# import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
#
# @RequestMapping("/api/{n.lower()}")
# @RestController
# class {n}Controller {'{'}
# {'}'}
#         """)


# for n, _ in entities:
#     if n in ign: continue
#     if n in base_entities:
#         wr(f"repository/{n}Repository.kt", f"""
# package ru.bardinpetr.itmo.meddelivery.common.auth.repository
#
# import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
# import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository
#
# interface {n}Repository : ICommonRestRepository<{n}>
#         """)
#     else:
#         wr(f"repository/{n}Repository.kt", f"""
# package ru.bardinpetr.itmo.meddelivery.common.auth.repository
#
# import ru.bardinpetr.itmo.meddelivery.app.entities.{n}
# import ru.bardinpetr.itmo.meddelivery.app.entities.{n}Id
# import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseCommonRestRepository
#
# interface {n}Repository : IBaseCommonRestRepository<{n}, {n}Id>
#         """)
