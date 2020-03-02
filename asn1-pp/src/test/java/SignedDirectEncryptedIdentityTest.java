import generated.asn1.SignedDirectEncryptedIdentityv2;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class SignedDirectEncryptedIdentityTest {

    @Test
    public void extraElements() {
        var s = "MIICPgYLYIQQAYdrCgECCgIwggHLMIIBVwYLYIQQAYdrCgECCQICAQECAQEWFDk5MDAwMDAxMjMxMjM0NTY3ODkwFhQ5OTk5MDAyMDAwMDAwMDAwMDAwMgIEATQXfzCB+QRRBBpjW+tcDbByOUY0Rr+lvNgt/Afq+ull626leQGXWDzToZYeqOjw7MZYbyp8ethgEd9q3aP5ty0vwCaKs2T7srO+V8WY6th1Paxjok7lk8YcBFEELaI6YNgch1PmKurg6GTSnvO9+MHe4K93Q4FJqQXJYw+yOmeXN78z6CceqEAsCOYpc94B/rcCK0XGRPq/Am9bQzMrna4tv/3KDqGs4owCIFIEUQRghvqwSLm8pNI18foR8iapjGhWIb6SxetiRd6a0iPdDcgCrMdEtd3ac3RP1hcUDrQ+04/f3cIWh1KA35GlTm6AbLy9mijcTuscmco+6MXmBYEUOTk5OTAwNDAwMDAwMDAwMDAwMDEEELNm8QS4RsqO8/J5/+6PWWECAQEWCDIwMTkwOTAxok8wDhYHVEFHMDFfSQIDAeJAMCEWB1RBRzAxX1MMFlRoZSBRdWljayBCcm93biBGb3guLi4wGhYHVEFHMDFfQgQPAQIDBAUGBwgJCgsMDQ4PMGAGCCqGSM49BAMDMFQCKF25kkCUDXgpnuMnIFWM3NSMRVp9/wi/2kLYGvp8P5WhO2Flqk7Dc8MCKAjPNneiECX0y1OijqrxBs1asnG9Dk8H7Rl8JKR+wqIv+5HBCy3TiPY=";
        var bytes = Base64.getDecoder().decode(s);
        var target = new SignedDirectEncryptedIdentityv2();
        target.decodeByteArray(bytes);
//        assertEquals("{\"notationIdentifier\":\"2.16.528.1.1003.10.1.2.10.2\",\"signedDEI\":{\"directEncryptedIdentity\":{\"notationIdentifier\":\"2.16.528.1.1003.10.1.2.9.2\",\"schemeVersion\":\"1\",\"schemeKeySetVersion\":\"1\",\"creator\":\"99000001231234567890\",\"recipient\":\"99990020000000000002\",\"recipientKeySetVersion\":\"20191103\",\"points\":[\"BBpjW+tcDbByOUY0Rr+lvNgt/Afq+ull626leQGXWDzToZYeqOjw7MZYbyp8ethgEd9q3aP5ty0vwCaKs2T7srO+V8WY6th1Paxjok7lk8Yc\",\"BC2iOmDYHIdT5irq4Ohk0p7zvfjB3uCvd0OBSakFyWMPsjpnlze/M+gnHqhALAjmKXPeAf63AitFxkT6vwJvW0MzK52uLb/9yg6hrOKMAiBS\",\"BGCG+rBIubyk0jXx+hHyJqmMaFYhvpLF62JF3prSI90NyAKsx0S13dpzdE/WFxQOtD7Tj9/dwhaHUoDfkaVOboBsvL2aKNxO6xyZyj7oxeYF\"],\"authorizedParty\":\"99990040000000000001\"},\"auditElement\":\"s2bxBLhGyo7z8nn/7o9ZYQ==\",\"signingKeyVersion\":\"1\",\"issuanceDate\":\"20190901\",\"extraElements\":[{\"key\":\"TAG01_I\",\"value\":\"123456\"},{\"key\":\"TAG01_S\",\"value\":\"The Quick Brown Fox...\"},{\"key\":\"TAG01_B\",\"value\":\"AQIDBAUGBwgJCgsMDQ4P\"}]},\"signatureValue\":{\"signatureType\":\"1.2.840.10045.4.3.3\",\"signatureValue\":{\"r\":\"782012286952681586253718419727116489162383141981080120806040277923802580511981484982674020070339\",\"s\":\"73503192518868422121955618750538837573743341864621777860678790157134233447609209196501204699382\"}}}", target.toString());
    }

    @Test
    public void noExtraElements() {
        var s = "MIIB7wYLYIQQAYdrCgECCgIwggF6MIIBVwYLYIQQAYdrCgECCQICAQECAQEWFDk5MDAwMDAxMjMxMjM0NTY3ODkwFhQ5OTk5MDAyMDAwMDAwMDAwMDAwMQIEATPtxDCB+QRRBA/H0DMqmLFrU+qP/+wThiHGnmYt1OY8PEXbPO5Ds+JJJPJhbF9HX8Z+1pAAOhM+kjpXLAcm+9wi3kyEvc+TmZWPhNZcJ4BdPeUAjm6i5GMQBFEEuRIxeenGiWWDqbPK6iOhzxN7DBrrdV4FugcU2ILUft7YU52BfGEVVmxfQSTKr8Zm8ILQjMLP4y0MWrsGr4CC6hQjVBarqWkXOnoJiIoQb/oEUQQwNecVsEcX8Eix4i203cq+UcO/NSxC77k9HwogaaX4haj2rkWKUTevHD3LVymZVoXJ65DAeUgyLmLmJ4QLClikDnrMSO7YaqQo3d+wztXqb4EUOTk5OTAwNDAwMDAwMDAwMDAwMDEEEAIcEZ9nZbMmV45tRHQyQtECAQEWCDIwMTkwOTAxMGIGCCqGSM49BAMDMFYCKQC2JlHuhloJGtmF3FUNzMlbIwSClRzpcwDqlCsFCSN9qzylbtGXXkzfAikAxLEsQILjz5A1tCxVdHeVq4BYpbbzMBYNtIk/ir902U4Kp6LdNWTgEw==";
        var bytes = Base64.getDecoder().decode(s);
        var target = new SignedDirectEncryptedIdentityv2();
        target.decodeByteArray(bytes);
//        assertEquals("{\"notationIdentifier\":\"2.16.528.1.1003.10.1.2.10.2\",\"signedDEI\":{\"directEncryptedIdentity\":{\"notationIdentifier\":\"2.16.528.1.1003.10.1.2.9.2\",\"schemeVersion\":\"1\",\"schemeKeySetVersion\":\"1\",\"creator\":\"99000001231234567890\",\"recipient\":\"99990020000000000001\",\"recipientKeySetVersion\":\"20180420\",\"points\":[\"BA/H0DMqmLFrU+qP/+wThiHGnmYt1OY8PEXbPO5Ds+JJJPJhbF9HX8Z+1pAAOhM+kjpXLAcm+9wi3kyEvc+TmZWPhNZcJ4BdPeUAjm6i5GMQ\",\"BLkSMXnpxollg6mzyuojoc8Tewwa63VeBboHFNiC1H7e2FOdgXxhFVZsX0Ekyq/GZvCC0IzCz+MtDFq7Bq+AguoUI1QWq6lpFzp6CYiKEG/6\",\"BDA15xWwRxfwSLHiLbTdyr5Rw781LELvuT0fCiBppfiFqPauRYpRN68cPctXKZlWhcnrkMB5SDIuYuYnhAsKWKQOesxI7thqpCjd37DO1epv\"],\"authorizedParty\":\"99990040000000000001\"},\"auditElement\":\"AhwRn2dlsyZXjm1EdDJC0Q==\",\"signingKeyVersion\":\"1\",\"issuanceDate\":\"20190901\"},\"signatureValue\":{\"signatureType\":\"1.2.840.10045.4.3.3\",\"signatureValue\":{\"r\":\"1519802232342194709092821652001609241694364001129897805680887555749327243392386413095430119115999\",\"s\":\"1641139594193823806166950764025513518609033289235067195918007026221360767601629097880322146885651\"}}}", target.toString());
    }

}
