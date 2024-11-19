- create:
    - form for req+re
    - drone_type
    - drone
    - wh
    - mf
    - product_type

- crud:
    - warehouse_products

- readonly:
    - request
    - r_e
    - flask
    - drone
    - mf
    - wh
    - nofz?
    - product_type
    -

- ?:
    - route + rp
    - user

- user sends request+list<RE>
    - insert req+re
    - trigger One<re>+One<drone>:IDLE --> New<flask>:QUEUED + Updated<re> + Updated<drone>:READY
    - trigger One<flask>+One<wp> --> Updated<wp> + Updated<flask>:PACKING
    - trigger One<flask> --> Updated<flask SET route>

- wh manager send drone (from table of drones)

- drone simulator fly over route

- view map

- 

