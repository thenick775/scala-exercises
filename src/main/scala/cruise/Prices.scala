package Cruise

object Prices {
  // defined by rate code and rate group
  case class Rate(rateCode: String, rateGroup: String)

  // cabin with associated code and price, belongs to a rate code
  case class CabinPrice(cabinCode: String, rateCode: String, price: BigDecimal)

  // aggregated data structure associating cabins, rates, prices, and rate groups
  case class BestGroupPrice(
      cabinCode: String,
      rateCode: String,
      price: BigDecimal,
      rateGroup: String
  )

  def getBestGroupPrices(
      rates: Seq[Rate],
      prices: Seq[CabinPrice]
  ): Seq[BestGroupPrice] =
    // easy group access by unique code, if this list is large we could
    // instead loop over the existing list in memory at the expense of lookup time
    val codeToGroup = rates.map(r => (r.rateCode, r.rateGroup)).toMap

    prices
      // group prices by rate code
      .groupBy(cp => (cp.cabinCode, codeToGroup.getOrElse(cp.rateCode, None)))
      // extract grouped prices sharing the tuple above
      .values
      // map over each group of prices, extracting the min price per group
      // associate this to the rate code we belong to
      .flatMap(groupedPrices => {
        // grab the smallest price from the current rate group
        groupedPrices
          .minByOption(op => op.price)
          .collect({
            // check if rate associated with min price exists
            case minPrice
                if codeToGroup.getOrElse(minPrice.rateCode, None) != None =>
              BestGroupPrice(
                minPrice.cabinCode,
                minPrice.rateCode,
                minPrice.price,
                codeToGroup(minPrice.rateCode)
              )
          })
      })
      // necessary to access sorting methods (see requirements)
      .toSeq
      .sortBy(bp => (bp.price))
}
