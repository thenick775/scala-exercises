import Cruise.Prices.{Rate, CabinPrice, getBestGroupPrices}
import Cruise.Promotions.{Promotion, allCombinablePromotions, combinablePromotions}

@main def hello(): Unit =
  println("Problem 1:\n")

  // data setup
  val rates = Seq(
    Rate("M1", "Military"),
    Rate("M2", "Military"),
    Rate("S1", "Senior"),
    Rate("S2", "Senior")
  )

  val prices = Seq(
    CabinPrice("CA", "M1", 200.00),
    CabinPrice("CA", "S1", 225.00),
    CabinPrice("CA", "S2", 260.00),
    CabinPrice("CB", "M1", 230.00),
    CabinPrice("CA", "M2", 250.00),
    CabinPrice("CB", "M2", 260.00),
    CabinPrice("CB", "S1", 245.00),
    CabinPrice("CB", "S2", 270.00)
  )

  println("Sequence of BestGroupPrice:\n")
  println(getBestGroupPrices(rates, prices).mkString("\n").concat("\n"))

  // -----------------------------------------------------------------

  println("Problem 2:")

  // data setup
  val promotions = Seq(
    Promotion("P1", Seq("P3")), // P1 is not combinable with P3
    Promotion("P2", Seq("P4", "P5")), // P2 is not combinable with P4 and P5
    Promotion("P3", Seq("P1")), // P3 is not combinable with P1
    Promotion("P4", Seq("P2")), // P4 is not combinable with P2
    Promotion("P5", Seq("P2")) // P5 is not combinable with P2
  )

  println("All PromotionCombos combinations:\n")
  println(allCombinablePromotions(promotions).mkString("\n").concat("\n"))

  println("PromotionCombos for promotionCode=\"P1\":\n")
  println(combinablePromotions("P1", promotions).mkString("\n").concat("\n"))

  println("PromotionCombos for promotionCode=\"P3\":\n")
  println(combinablePromotions("P3", promotions).mkString("\n").concat("\n"))