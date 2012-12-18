import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodb.model.ScanRequest;
import com.amazonaws.services.dynamodb.model.ScanResult;

public class Sample {

    static AmazonDynamoDBClient dynamoDB;
    private static void init() throws Exception {
        AWSCredentials cred = new PropertiesCredentials(Sample.class.getResourceAsStream("AwsCredentials.secret"));
        dynamoDB = new AmazonDynamoDBClient(cred);
        dynamoDB.setEndpoint("dynamodb.ap-northeast-1.amazonaws.com");
    }

    public static void main(String[] args) throws Exception {
        init();
        try {
            String tableName = "products";
            /*
            HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
            Condition condition = new Condition()
                .withComparisonOperator(ComparisonOperator.GT.toString())
                .withAttributeValueList(new AttributeValue().withN("1985"));
            scanFilter.put("year", condition);
            ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
            */
            ScanRequest scanRequest = new ScanRequest(tableName);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            System.out.println("Result: " + scanResult);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered a serious internal problem while trying to communicate with AWS, such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}
