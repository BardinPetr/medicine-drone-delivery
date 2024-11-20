import json
def run(data):
    return json.dumps([data['p1'], data['p2']])
data = json.loads(input())
print(run(data))
