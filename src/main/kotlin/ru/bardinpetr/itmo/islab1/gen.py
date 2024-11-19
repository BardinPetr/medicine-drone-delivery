import os

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

print(base_entities)