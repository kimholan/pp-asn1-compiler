package descriptor

import groovy.transform.ToString
import org.bouncycastle.asn1.ASN1ObjectIdentifier

@ToString(ignoreNulls = true)
class AsnFieldDescriptor {

    String asnType
    String javaType
    String javaTypeParameter;
    String identifier
    String value
    boolean schemaType
    boolean optional
    Integer tagClassNumber
    AsnClassDescriptor classDescriptor

    boolean isObjectIdentifier() {
        asnType == ASN1ObjectIdentifier.simpleName
    }

    boolean isTagged() {
        tagClassNumber != null
    }


}
