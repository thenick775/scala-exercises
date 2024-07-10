import munit._

class CruisePricingSuite extends munit.FunSuite {
  import Cruise.Prices._

  // getBestGroupPrices
  test("should return correct results for valid input") {
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

    val expected = Seq(
      BestGroupPrice("CA", "M1", 200.00, "Military"),
      BestGroupPrice("CA", "S1", 225.00, "Senior"),
      BestGroupPrice("CB", "M1", 230.00, "Military"),
      BestGroupPrice("CB", "S1", 245.00, "Senior")
    )

    val result = getBestGroupPrices(rates, prices)

    assertEquals(result, expected)
  }

  test("should handle empty input gracefully") {
    val rates = Seq(
      Rate("M1", "Military")
    )

    val prices = Seq(
      CabinPrice("CA", "M1", 200.00)
    )

    // all empty
    val resultAll = getBestGroupPrices(Seq.empty, Seq.empty)
    assertEquals(resultAll, Seq.empty)
    // partially empty with rates
    val resultPartialRates = getBestGroupPrices(rates, Seq.empty)
    assertEquals(resultPartialRates, Seq.empty)
    // partially empty with prices
    val resultPartialPrices = getBestGroupPrices(Seq.empty, prices)
    assertEquals(resultPartialPrices, Seq.empty)
  }
}

class CruisePromotionSuite extends munit.FunSuite {
  import Cruise.Promotions._

   // allCombinablePromotions
   test("should return correct results for valid input") {
    val promotions = Seq(
      Promotion("P1", Seq("P3")),
      Promotion("P2", Seq("P4", "P5")),
      Promotion("P3", Seq("P1")),
      Promotion("P4", Seq("P2")),
      Promotion("P5", Seq("P2"))
    )

    val expectedCombos = Seq(
      PromotionCombo(Seq("P1", "P2")),
      PromotionCombo(Seq("P1", "P4", "P5")),
      PromotionCombo(Seq("P2", "P3")),
      PromotionCombo(Seq("P3", "P4", "P5"))
    )

    val result = allCombinablePromotions(promotions)

    assertEquals(result, expectedCombos)
  }

  test("should return empty list for empty promotions list") {
    val result = allCombinablePromotions(Seq.empty)

    assertEquals(result, Seq.empty)
  }
}
