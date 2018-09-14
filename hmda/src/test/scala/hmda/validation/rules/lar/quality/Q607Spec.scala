package hmda.validation.rules.lar.quality

import com.typesafe.config.ConfigFactory
import hmda.model.filing.lar.LoanApplicationRegister
import hmda.validation.rules.EditCheck
import hmda.validation.rules.lar.LarEditCheckSpec
import hmda.model.filing.lar.LarGenerators._
import hmda.model.filing.lar.enums.SecuredBySubordinateLien

class Q607Spec extends LarEditCheckSpec {
  override def check: EditCheck[LoanApplicationRegister] = Q607

  property("Subordinate Lien Loans should be less than 250000") {
    forAll(larGen) { lar =>
      val config = ConfigFactory.load()
      val loanAmount =
        config.getDouble("hmda.validation.quality.Q607.loan.amount")

      val relevantLar = lar.copy(lienStatus = SecuredBySubordinateLien)
      whenever(lar.lienStatus != SecuredBySubordinateLien) {
        lar.mustPass
      }
      whenever(lar.loan.amount >= loanAmount) {
        lar.copy(lienStatus = SecuredBySubordinateLien).mustFail
      }
      relevantLar.copy(loan = lar.loan.copy(amount = loanAmount)).mustFail
      relevantLar.copy(loan = lar.loan.copy(amount = loanAmount - 1)).mustPass
    }
  }
}
