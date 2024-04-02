from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options

# Set up Chrome options (headless mode)
chrome_options = Options()
chrome_options.add_argument("--headless")

# Initialize the WebDriver
driver = webdriver.Chrome(options=chrome_options)

# URL to test
url = "https://waardamme.bakkerijkerkhove.be/be-nl/bakkerijkerkhove-waardamme/product/230137/fjord"

# Open the URL
driver.get(url)

# Wait for the description element to be visible
try:
    product_info_elements = WebDriverWait(driver, 10).until(
        EC.visibility_of_all_elements_located((By.CSS_SELECTOR, 'div.product-info div.item'))
    )

    # Loop through each item within product-info
    for product_info_element in product_info_elements:
        # Extract title text and corresponding content using JavaScript
        title_element = driver.execute_script("return arguments[0].querySelector('h3').textContent.trim();", product_info_element)
        content_element = driver.execute_script("return arguments[0].querySelector('h3 + p').textContent.trim();", product_info_element)

        # Check if the title is Beschrijving or Ingrediënten and extract the corresponding text
        if title_element == "Beschrijving":
            description = content_element if content_element else None
            print("Description:", description)
        elif title_element == "Ingrediënten":
            ingredients = content_element if content_element else None
            print("Ingredients:", ingredients)
        else:
            print("Unknown title element:", title_element)

except Exception as e:
    print("Error occurred while extracting ingredients:", str(e))
    
# Quit the driver
driver.quit()
