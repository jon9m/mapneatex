import net.andreinc.mapneat.dsl.json
import net.andreinc.mapneat.model.MapNeatSource.Companion.fromObject
import net.andreinc.mockneat.unit.objects.From.from
import java.util.*
import kotlin.collections.HashSet

fun main() {

    val theInitialUserDto = transform1()

    println(theInitialUserDto)
}

public fun transform1(): String {
    val result = "";




    return result;
}

val docGenReq = "{\n" +
        "    \"taskId\": \"123-1323-123-12-312-323\",\n" +
        "    \"orderId\": \"ORD-123-12345\",\n" +
        "    \"documents\": [\n" +
        "        {\n" +
        "            \"documentData\" : {\n" +
        "                \"accounts\": [\n" +
        "                    {\n" +
        "                        \"accountBsb\" : \"083100\",\n" +
        "                        \"currencyCode\": \"AUD\",\n" +
        "                        \"accountNumber\": \"5675675675\"\n" +
        "                    }\n" +
        "                ],\n" +
        "                \"associatedCustomers\": [\n" +
        "                    {\n" +
        "                        \"partyType\": \"ORG\",\n" +
        "                        \"customerId\": \"676786786\"\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"documentRecipients\": [\n" +
        "                {\n" +
        "                    \"partyType\": \"IND\",\n" +
        "                    \"customerId\": \"564545\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"partyType\": \"IND\",\n" +
        "                    \"customerId\": \"23242\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"partyType\": \"IND\",\n" +
        "                    \"customerId\": \"345645\"\n" +
        "                }\n" +
        "            ],\n" +
        "            \"documentTemplateId\": \"DebitCardConsent\",\n" +
        "            \"orderWorkflowState\": \"DEBITCARDCONSENT\"\n" +
        "        }\n" +
        "    ]\n" +
        "}";


val graphResp = "{\n" +
        "  \"data\":{\n" +
        "      \"ind\": [\n" +
        "          {\n" +
        "              \"customerId\" : \"123456789\",\n" +
        "              \"legacyId\" : \"456789\",\n" +
        "              \"title\": \"Mr\",\n" +
        "              \"givenName1\" : \"Clark\",\n" +
        "              \"givenName2\" : \"Superman\",\n" +
        "              \"givenName3\" : null,\n" +
        "              \"familyName\" : \"Kent\"\n" +
        "          }\n" +
        "      ],\n" +
        "      \"org\" : [\n" +
        "          {\n" +
        "              \"customerId\": \"123456789\",\n" +
        "              \"legacyId\": \"456789\",\n" +
        "              \"customerName\": \"ABC org pvt ltd\"\n" +
        "          }\n" +
        "      ]\n" +
        "  }\n" +
        "}";




public fun transform(): String {
    val users: Map<Long, User> = getUsers(100)
    val aRandomUser = from(users.values.toMutableList()).get()

    val theInitialUser = json(fromObject(aRandomUser)) {
        copySourceToTarget()
    }.getPrettyString()
    println(theInitialUser)

    val theInitialUserDto = json(fromObject(aRandomUser)) {

        // Copy everything from the object (source) to the target
        "" *= "$"

        // Removes the visits node
        -"visits"

        // Removes the creditCards node
        -"creditCards"

        // Removes the pwd node
        -"pwd"


        // Creates a new "visited" node containing all the country names that were visited
        // To avoid country duplication we keep the results in a Set
        "visited" *= {
            expression = "$.visits[*].country"
            processor = {
                val result = HashSet<String>()
                result.addAll(it.takeIf { true } as LinkedList<String>)
                result
            }
        }


        // We modify the lastName field and make it uppercase
        "lastName" /= {
            targetCtx().read<String>("$.lastName").toUpperCase()
        }

        // We modify the friends field to contain actually names and first names
        // instead of users ids
        "friends" /= {
            targetCtx()
                .read<ArrayList<Long>>("$.friends")
                .map { (users[it]?.firstName + " " + users[it]?.lastName) }
                .toList()
        }
    }.getPrettyString()
    return theInitialUserDto
}

