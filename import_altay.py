import requests
from requests.auth import HTTPBasicAuth

BASE_URL = "http://localhost:8080"  # Change if your backend runs elsewhere
AUTH = HTTPBasicAuth("admin", "admin123")

def create_location(address):
    url = f"{BASE_URL}/location"
    payload = {
        "regionId": 196,
        "nameRu": address,
        "nameKz": address,
        "nameEn": address
    }
    resp = requests.post(url, json=payload, auth=AUTH)
    resp.raise_for_status()
    # Assuming the response is like: {"id": 123, ...} or just the id
    try:
        data = resp.json()
        if isinstance(data, dict) and "id" in data:
            return data["id"]
        elif isinstance(data, int):
            return data
        else:
            # If the API returns a string id
            return int(data)
    except Exception:
        # If the API returns the id as plain text
        return int(resp.text.strip())

def create_apk(location_id, apk_name, cert_name, cert_issue, cert_expiry):
    url = f"{BASE_URL}/apk"
    payload = {
        "locationId": location_id,
        "deviceNumber": apk_name,
        "certNumber": cert_name,
        "certIssue": cert_issue,
        "certExpiry": cert_expiry
    }
    resp = requests.post(url, json=payload, auth=AUTH)
    resp.raise_for_status()
    data = resp.json()
    if isinstance(data, dict) and "id" in data:
        return data["id"]
    elif isinstance(data, int):
        return data
    else:
        return int(resp.text.strip())

def create_camera(apk_id, camera_name, camera_ip, camera_direction):
    url = f"{BASE_URL}/camera"
    payload = {
        "apkId": apk_id,
        "name": camera_name,
        "ip": camera_ip,
        "code": camera_name,
        "direction": camera_direction,
        "brand": "Mergen",
        "collectionDays": 90
    }
    resp = requests.post(url, json=payload, auth=AUTH)
    resp.raise_for_status()
    data = resp.json()
    if isinstance(data, dict) and "id" in data:
        return data["id"]
    elif isinstance(data, int):
        return data
    else:
        return int(resp.text.strip()) 