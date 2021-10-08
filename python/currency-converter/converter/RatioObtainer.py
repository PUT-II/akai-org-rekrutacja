import datetime
import json
import os
import urllib.request


class RatioObtainer:
    __BASE_URL: str = "http://api.exchangeratesapi.io/v1/latest"
    __ACCESS_KEY: str = "50e6cc06cee62102c5cf30d1fe6bf334"

    def __init__(self, base: str, target: str):
        self.base: str = base
        self.target: str = target

    def was_ratio_saved_today(self) -> bool:
        if not os.path.isfile("ratios.json"):
            return False

        with open("ratios.json", mode="r") as file:
            ratios_str = file.read()

        ratios = json.loads(ratios_str) if ratios_str != "" else {}
        if self.base in ratios and self.target in ratios[self.base]:
            date_fetched = ratios[self.base][self.target]["date_fetched"]
            current_date = datetime.date.strftime(datetime.date.today(), "%Y-%m-%d")

            if current_date == date_fetched:
                return True
        else:
            return False

    def fetch_ratio(self):
        if self.was_ratio_saved_today():
            return

        url = f"{self.__BASE_URL}?access_key={self.__ACCESS_KEY}&base={self.base}&symbols={self.target}"
        with urllib.request.urlopen(url) as request:
            response = json.loads(request.read())

        self.save_ratio(response["rates"][self.target])

    def save_ratio(self, ratio):
        with open("ratios.json", mode="w+") as file:
            ratios_str = file.read()

            ratios = json.loads(ratios_str) if ratios_str != "" else {}

            if self.base not in ratios:
                ratios[self.base] = {}

            if self.target not in ratios[self.base]:
                ratios[self.base][self.target] = {}

            ratios[self.base][self.target]["ratio"] = ratio
            ratios[self.base][self.target]["date_fetched"] = datetime.date.strftime(datetime.date.today(), "%Y-%m-%d")

            file.write(json.dumps(ratios, indent=1))

    def get_matched_ratio_value(self):
        with open("ratios.json", mode="r") as file:
            ratios_str = file.read()
            ratios = json.loads(ratios_str) if ratios_str != "" else {}
            return ratios[self.base][self.target]["ratio"]
