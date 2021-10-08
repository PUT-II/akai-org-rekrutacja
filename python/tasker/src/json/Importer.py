import json


class Importer:

    def __init__(self):
        self.__encoded_tasks: str = ""
        pass

    def read_tasks(self):
        with open("taski.json", mode="r", encoding="utf8") as file:
            self.__encoded_tasks = file.read()

    def get_tasks(self):
        return json.loads(self.__encoded_tasks)
