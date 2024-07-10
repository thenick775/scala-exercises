package Cruise

object Promotions {
  case class Promotion(code: String, notCombinableWith: Seq[String])

  case class PromotionCombo(promotionCodes: Seq[String])

  // helper functions
  def isSubset(set1: Set[String], set2: Set[String]): Boolean =
    set1.subsetOf(set2) && set1 != set2

  def allCombinablePromotions(
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] =
    // use an accumulator while stepping through the list to find all compatible combinations
    allPromotions
      .foldLeft(Seq(Set.empty[String])) { (acc, promotion) => // like map reduce
        // map over the accumulator, adding the current promo if combinable
        acc.flatMap(combo =>
          if (combo.forall(code => !promotion.notCombinableWith.contains(code)))
            Seq(combo + promotion.code, combo)
          else
            Seq(combo)
        )
      }
      // ensures that we return an empty seq if no promotions were given
      .collect({ case combo if combo.nonEmpty => PromotionCombo(combo.toSeq) })
      // accumulate values that are not subsets of other promo code combos
      // note: could maybe use array methods, but this seemed simpler
      .foldLeft(Seq.empty[PromotionCombo]) { (acc, combo) =>
        if (
          !acc.exists(e =>
            isSubset(combo.promotionCodes.toSet, e.promotionCodes.toSet)
          )
        )
          acc :+ combo
        else
          acc
      }

  def combinablePromotions(
      promotionCode: String,
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] =
    // grab all the promotion combinations, check if the promotion codes in the combo contain our desired code
    allCombinablePromotions(allPromotions).filter(promo =>
      promo.promotionCodes.contains(promotionCode)
    )
}
