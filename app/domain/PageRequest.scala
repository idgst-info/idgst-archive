package domain

/**
  * Case class for pagination information.
  *
  * @param pageNumber      [[Int]] the page number with records to be retrieved
  * @param pageSize        [[Int]] amount of records the page should contain
  * @param sortingCriteria [[SortingCriteria]] sorting information
  */
case class PageRequest(pageNumber: Int, pageSize: Int, sortingCriteria: SortingCriteria)