import json


class Exporter:

    def __init__(self):
        pass

    def save_tasks(self, tasks):
        tasks_json = json.dumps(tasks, indent=1)
        with open("taski.json", mode="w", encoding="utf8") as file:
            file.write(tasks_json)
