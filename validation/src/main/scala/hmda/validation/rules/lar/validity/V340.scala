package hmda.validation.rules.lar.validity

import hmda.model.fi.lar.LoanApplicationRegister
import hmda.validation.dsl.Result
import hmda.validation.rules.EditCheck
import hmda.validation.dsl.PredicateDefaults._
import hmda.validation.dsl.PredicateSyntax._

object V340 extends EditCheck[LoanApplicationRegister] {

  def apply(lar: LoanApplicationRegister): Result = {
    lar.purchaserType is containedIn(0 to 9)
  }

  def name = "V340"
}
