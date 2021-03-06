package hmda.api.http.model.filing.submissions

import hmda.model.edits.EditDetailsRow

case class EditDetailsSummary(editName: String = "",
                              rows: Seq[EditDetailsRow] = Nil,
                              path: String = "",
                              currentPage: Int = 0,
                              total: Int = 0)
    extends PaginatedResponse {
  def isEmpty: Boolean =
    this.rows == Nil && this.editName == "" && this.path == "" && this.currentPage == 0 && this.total == 0
}
