import requests

def InternetCheck():

    def check_internet():
        
        try:
            response = requests.get('https://duckduckgo.com/', timeout=5)
            return True if response.status_code == 200 else False
        
        except requests.ConnectionError:
            return False
        
        except requests.Timeout:
            return False

    if check_internet():
        return 1
    
    else:
        return 0