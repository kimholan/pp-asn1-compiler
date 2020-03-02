package descriptor


import groovy.transform.ToString

@ToString(ignoreNulls = true)
class AsnClassDescriptor {

    String asnType

    String javaType

    String identifier

    List<AsnFieldDescriptor> fields = []

}
