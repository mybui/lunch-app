package models

class Meal(title: String, price: String, diet: String, allergy: String) {
  override def toString(): String = {
    return title.replace("\"", "") + " at " +
      price.replace("\"", "") + ". Diet: " +
      diet.replace("\"", "") + ". Allergy: " +
      allergy.replace("\"", "")
  }
}