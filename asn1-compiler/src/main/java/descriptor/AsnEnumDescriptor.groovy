package descriptor

import groovy.transform.ToString

@ToString(ignoreNulls = true)
class AsnEnumDescriptor {

    String identifier
    List<AsnFieldDescriptor> fields = []

}
