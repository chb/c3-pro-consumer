package org.bch.c3pro.consumer.config;

import org.apache.commons.io.IOUtils;
import org.bch.c3pro.consumer.exception.C3PROException;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class that manages the configuration parameters
 * @author CHIP-IHL
 */
public class AppConfig {

    public static final String CONFIG_PROPERTIES_FILE=          "config.properties";

    public static final String AWS_SQS_URL =                    "app.aws.sqs.url";
    public static final String AWS_SQS_PROFILE =                "app.aws.sqs.profile";
    public static final String AWS_SQS_REGION =                 "app.aws.sqs.region";
    public static final String AWS_SQS_NAME = 			        "app.aws.sqs.name";


    // The key posted in the metadata part of the message to SQS. The value will contain the ncrypted symetric key
    // to decrypt the message
    public static final String SECURITY_METADATAKEY =           "app.security.metadatakey";
    public static final String SECURITY_METADATAKEY_ID =        "app.security.metadatakeyid";

    public static final String FHIR_METADATA_VERSION =          "app.fhir.metadata.version";

    public static final String FHIR_VERSION_DEFAULT =           "app.fhir.version.default";

    public static final String SECURITY_PRIVATEKEY_ALG =        "app.security.privatekey.algorithm";
    public static final String SECURITY_PRIVATEKEY_BASEALG =    "app.security.privatekey.basealgorithm";
    public static final String SECURITY_PRIVATEKEY_FILENAME =   "app.security.privatekey.filename";
    public static final String SECURITY_PRIVATEKEY_BASEPATH =   "app.security.privatekey.basepath";
    public static final String SECURITY_SECRETKEY_BASEALG =     "app.security.secretkey.basealgorithm";
    public static final String SECURITY_SECRETKEY_ALG = 		"app.security.secretkey.algorithm";
    public static final String SECURITY_PRIVATEKEY_SIZE =       "app.security.secretkey.size";

    public static final String C3PRO_SERVER_HOST =              "app.c3pro.server.host";
    public static final String C3PRO_SERVER_PORT =              "app.c3pro.server.port";
    public static final String C3PRO_SERVER_TRANS =             "app.c3pro.server.transport";
    public static final String C3PRO_SERVER_CREDENTIALS=        "app.authfile.c3pro.server";
    public static final String C3PRO_CONSUMER_DATASOURCE=       "app.c3pro.consumer.datasourcefile";

    public static final String HOST_FHIR_I2B2=                  "app.host.fhir.i2b2";
    public static final String ENDPOINT_FHIR_I2B2_ROOT=         "app.endpoint.fhir.i2b2.root";
    public static final String PORT_FHIR_I2B2=                  "app.port.fhir.i2b2";
    public static final String PROTOCOL_FHIR_I2B2=              "app.network.protocol.fhir.i2b2";

    public static final String UTF = "UTF-8";

    public static final int HTTP_TRANSPORT_BUFFER_SIZE = 500;
    private static Properties prop = new Properties();
    /**
     * Upload the configuration from config.properties files
     */
    private static void uploadConfiguration() throws C3PROException {
        InputStream input = null;

        try {
            String filename = CONFIG_PROPERTIES_FILE;
            input = AppConfig.class.getResourceAsStream(filename);
            if (input == null) {
                throw new C3PROException("No " + filename + " found!");
            }
            prop.load(input);

        } catch (IOException ex) {
            throw new C3PROException("", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    throw new C3PROException("", e);
                }
            }
        }
    }

    /**
     * Get the value of the property described in conf.properties file
     * @param key The key of the property
     * @return The value of the property.
     * @throws C3PROException If the property key does not exist.
     */
    public static String getProp(String key) throws C3PROException {
        if (prop.isEmpty()) {
            uploadConfiguration();
        }
        return prop.getProperty(key);
    }

    /**
     * Returns the content of the the credential file whsoe path is encoded in the value of the key parameter
     * @param key The key of the property in config.properties that contains the path of the file
     * @return The content of the file
     * @throws IOException If there is a problem reading the file
     * @throws C3PROException In case the parameter is not well-formed
     */
    public static String getAuthCredentials(String key) throws IOException, C3PROException {
        String path = getProp(key);
        String finalPath = path;
        int i = path.indexOf("[");
        int j = path.indexOf("]");
        if (i<0 && j>=0) throw new C3PROException("Missing [ in " + key);
        if (i>=0) {
            if (j<0) throw new C3PROException("Missing ] in " + key);
            String var = path.substring(i+1,j);
            String aux = System.getenv(var);
            if (aux == null) aux = "";
            finalPath = path.replaceAll("\\[" + var + "\\]", aux);
        }
        FileInputStream inputStream = new FileInputStream(finalPath);
        String out=null;
        try {
            out = IOUtils.toString(inputStream).trim();
        } finally {
            inputStream.close();
        }
        return out;
    }
}
