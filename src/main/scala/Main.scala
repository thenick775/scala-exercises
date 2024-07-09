import Cruise.Prices.{Rate, CabinPrice, getBestGroupPrices}
import Cruise.Promotions.{Promotion}

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

