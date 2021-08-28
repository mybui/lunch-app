package models

class Meal(title: String, price: String, diet: String, allergy: String) {
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