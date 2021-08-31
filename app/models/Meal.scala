package models

class Meal(title: String, price: String, diet: String, allergy: String) {
  def filterDiet(info: String): Boolean = {
    if (diet.contains(info)) {
      return true
    }
    return false
  }

  def filterAllergy(info: String): Boolean = {
    if (allergy.contains(info)) {
      return true
    }
    return false
  }

  override def toString(): String = {
    return title.replace("\"", "").replace("\\", "").replace("!", "") +
      """
        |""".stripMargin + "                        • Price: " +
      price.replace("\"", "").replace("\\", "").replace("!", "") +
      """
        |""".stripMargin + "                        • Diet: " +
      diet.replace("\"", "").replace("\\", "").replace("!", "") +
      """
        |""".stripMargin + "                        • Allergy: " +
      allergy.replace("\"", "").replace("\\", "").replace("!", "")
  }
}