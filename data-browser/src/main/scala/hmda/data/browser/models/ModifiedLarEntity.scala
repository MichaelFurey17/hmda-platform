package hmda.data.browser.models

import java.sql.Timestamp

import slick.jdbc.GetResult

case class ModifiedLarEntity(
    id: Int,
    lei: String,
    loanType: Int,
    loanPurpose: Int,
    preapproval: Int,
    constructionMethod: String,
    occupancyType: Int,
    loanAmount: Double,
    actionTakenType: Int,
    state: String,
    county: String,
    tract: String,
    ethnicityApplicant1: String,
    ethnicityApplicant2: String,
    ethnicityApplicant3: String,
    ethnicityApplicant4: String,
    ethnicityApplicant5: String,
    ethnicityObservedApplicant: Int,
    ethnicityCoApplicant1: String,
    ethnicityCoApplicant2: String,
    ethnicityCoApplicant3: String,
    ethnicityCoApplicant4: String,
    ethnicityCoApplicant5: String,
    ethnicityObservedCoApplicant: Int,
    raceApplicant1: String,
    raceApplicant2: String,
    raceApplicant3: String,
    raceApplicant4: String,
    raceApplicant5: String,
    rateCoApplicant1: String,
    rateCoApplicant2: String,
    rateCoApplicant3: String,
    rateCoApplicant4: String,
    rateCoApplicant5: String,
    raceObservedApplicant: Int,
    raceObservedCoApplicant: Int,
    sexApplicant: Int,
    sexCoApplicant: Int,
    observedSexApplicant: Int,
    observedSexCoApplicant: Int,
    ageApplicant: String,
    applicantAgeGreaterThan62: String,
    ageCoApplicant: String,
    coapplicantAgeGreaterThan62: String,
    income: String,
    purchaserType: Int,
    rateSpread: String,
    hoepaStatus: Int,
    lienStatus: Int,
    creditScoreTypeApplicant: Int,
    creditScoreTypeCoApplicant: Int,
    denialReason1: String,
    denialReason2: String,
    denialReason3: String,
    denialReason4: String,
    totalLoanCosts: String,
    totalPoints: String,
    originationCharges: String,
    discountPoints: String,
    lenderCredits: String,
    interestRate: String,
    paymentPenalty: String,
    debtToIncome: String,
    loanValueRatio: String,
    loanTerm: String,
    rateSpreadIntro: String,
    baloonPayment: Int,
    insertOnlyPayment: Int,
    amortization: Int,
    otherAmortization: Int,
    propertyValue: String,
    homeSecurityPolicy: Int,
    landPropertyInterest: Int,
    totalUnits: String,
    mfAffordable: String,
    applicationSubmission: Int,
    payable: Int,
    aus1: Int,
    aus2: Int,
    aus3: Int,
    aus4: Int,
    aus5: Int,
    reverseMortgage: Int,
    lineOfCredits: Int,
    businessOrCommercial: Int,
    population: String,
    minorityPopulationPercent: String,
    ffiecMedFamIncome: String,
    tractToMsamd: String,
    ownerOccupiedUnits: String,
    oneToFourFamUnits: String,
    msaMd: Int,
    loanFlag: String,
    createdAt: Timestamp,
    submissionId: String,
    msaMdName: String,
    filingYear: Int,
    conformingLoanLimit: String,
    medianAge: Int,
    medianAgeCalculated: String,
    medianIncomePercentage: String,
    raceCategorization: String,
    sexCategorization: String,
    ethnicityCategorization: String,
    percentMedianMsaIncome: String
)

object ModifiedLarEntity {
  // See http://slick.lightbend.com/doc/3.2.0/sql.html?highlight=getresult#result-sets
  // we use shortcut << to get type inference instead of explicitly specifying nextInt or nextString based on the type
  implicit val getResult: GetResult[ModifiedLarEntity] =
    GetResult(
      r =>
        ModifiedLarEntity(
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<,
          r.<<
      ))

  def header =
    "id,lei,loanType,loanPurpose,preapproval,constructionMethod,occupancyType,loanAmount,actionTakenType,state,county,tract,ethnicityApplicant1,ethnicityApplicant2,ethnicityApplicant3,ethnicityApplicant4,ethnicityApplicant5,ethnicityObservedApplicant,ethnicityCoApplicant1,ethnicityCoApplicant2,ethnicityCoApplicant3,ethnicityCoApplicant4,ethnicityCoApplicant5,ethnicityObservedCoApplicant,raceApplicant1,raceApplicant2,raceApplicant3,raceApplicant4,raceApplicant5,rateCoApplicant1,rateCoApplicant2,rateCoApplicant3,rateCoApplicant4,rateCoApplicant5,raceObservedApplicant,raceObservedCoApplicant,sexApplicant,sexCoApplicant,observedSexApplicant,observedSexCoApplicant,ageApplicant,applicantAgeGreaterThan62,ageCoApplicant,coapplicantAgeGreaterThan62,income,purchaserType,rateSpread,hoepaStatus,lienStatus,creditScoreTypeApplicant,creditScoreTypeCoApplicant,denialReason1,denialReason2,denialReason3,denialReason4,totalLoanCosts,totalPoints,originationCharges,discountPoints,lenderCredits,interestRate,paymentPenalty,debtToIncome,loanValueRatio,loanTerm,rateSpreadIntro,baloonPayment,insertOnlyPayment,amortization,otherAmortization,propertyValue,homeSecurityPolicy,landPropertyInterest,totalUnits,mfAffordable,applicationSubmission,payable,aus1,aus2,aus3,aus4,aus5,reverseMortgage,lineOfCredits,businessOrCommercial,population,minorityPopulationPercent,ffiecMedFamIncome,tractToMsamd,ownerOccupiedUnits,oneToFourFamUnits,msaMd,loanFlag,createdAt,submissionId,msaMdName,filingYear,conformingLoanLimit,medianAge,medianAgeCalculated,medianIncomePercentage,raceCategorization,sexCategorization,ethnicityCategorization,percentMedianMsaIncome"

  implicit class CsvOps(modifiedLarEntity: ModifiedLarEntity) {
    import modifiedLarEntity._
    def toCsv =
      s"$id,$lei,$loanType,$loanPurpose,$preapproval,$constructionMethod,$occupancyType,$loanAmount,$actionTakenType,$state,$county,$tract,$ethnicityApplicant1,$ethnicityApplicant2,$ethnicityApplicant3,$ethnicityApplicant4,$ethnicityApplicant5,$ethnicityObservedApplicant,$ethnicityCoApplicant1,$ethnicityCoApplicant2,$ethnicityCoApplicant3,$ethnicityCoApplicant4,$ethnicityCoApplicant5,$ethnicityObservedCoApplicant,$raceApplicant1,$raceApplicant2,$raceApplicant3,$raceApplicant4,$raceApplicant5,$rateCoApplicant1,$rateCoApplicant2,$rateCoApplicant3,$rateCoApplicant4,$rateCoApplicant5,$raceObservedApplicant,$raceObservedCoApplicant,$sexApplicant,$sexCoApplicant,$observedSexApplicant,$observedSexCoApplicant,$ageApplicant,$applicantAgeGreaterThan62,$ageCoApplicant,$coapplicantAgeGreaterThan62,$income,$purchaserType,$rateSpread,$hoepaStatus,$lienStatus,$creditScoreTypeApplicant,$creditScoreTypeCoApplicant,$denialReason1,$denialReason2,$denialReason3,$denialReason4,$totalLoanCosts,$totalPoints,$originationCharges,$discountPoints,$lenderCredits,$interestRate,$paymentPenalty,$debtToIncome,$loanValueRatio,$loanTerm,$rateSpreadIntro,$baloonPayment,$insertOnlyPayment,$amortization,$otherAmortization,$propertyValue,$homeSecurityPolicy,$landPropertyInterest,$totalUnits,$mfAffordable,$applicationSubmission,$payable,$aus1,$aus2,$aus3,$aus4,$aus5,$reverseMortgage,$lineOfCredits,$businessOrCommercial,$population,$minorityPopulationPercent,$ffiecMedFamIncome,$tractToMsamd,$ownerOccupiedUnits,$oneToFourFamUnits,$msaMd,$loanFlag,$createdAt,$submissionId,$msaMdName,$filingYear,$conformingLoanLimit,$medianAge,$medianAgeCalculated,$medianIncomePercentage,$raceCategorization,$sexCategorization,$ethnicityCategorization,$percentMedianMsaIncome"
  }
}
