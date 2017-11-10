package domain

/**
  * Abstraction for sorting records.
  */
sealed trait SortingCriteria

object SortingCriteria {

  /**
    * Records should be sorted according to the provided parameters.
    *
    * @param sortOrder [[String]] sorting direction: 1 or -1 to specify an ascending or descending sort respectively
    * @param sortBy    [[String]] the field to sort by
    */
  final case class SortedBy(sortOrder: String, sortBy: String) extends SortingCriteria

  /**
    * Records should be retrieved without sorting.
    */
  final case object UnSorted extends SortingCriteria

  /**
    * Companion object for converting provided String sort order representation to compatible with Mongo sorting orders
    */
  object SortedBy {
    def apply(sortOrder: String, sortBy: String): SortedBy = {

      val order = sortOrder.toLowerCase match {
        case "desc" | "-1" => "-1"
        case _ => "1"
      }
      new SortedBy(order, sortBy)
    }
  }

}







