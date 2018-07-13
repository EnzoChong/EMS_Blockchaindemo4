package com.sap.summit.constants;

import java.math.BigDecimal;

/**
 * ClassName: CommonConstants Description: define common constants
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */

public interface CommonConstants //NOSONAR
{
    String TMP_USER_ID = "SAPIT";

    // constrains
    int DESCRIPTION_LENGTH = 300;
    int NEGATIVE_NUMBER = -1;

    int STATUS_CODE_LENGTH = 50;
    int STATUS_NAME_LENGTH = 100;
    int STATUS_DESCRIPTION_LENGTH = 300;
    String STATUS_CODE_REGEXP = "^[A-Za-z0-9_]+$";
    BigDecimal ZERO = new BigDecimal(0);

    // exceptions
    String EXCEPTION_NON_EXISTING = "EXCEPTION_HANDLER_NON_EXISTING";

    // common entity attributes
    String ENTITY_CREATEDON = "createdOn";
    String ENTITY_LANGUAGE = "language";
    String ENTITY_GUID = "guid";
    String ENTITY_ALL = "all";

    //symbol
    String BLANK = " ";
    String PERCENT = "%";
    String DOUBLE_QUOTATION_MARK = "";
    String COLON = ":";
    String CLOSING_BRACE = "\\(";

    //common length limitation
    int COMMON_CODE_LENGTH = 50;
    int COMMON_NAME_LENGTH = 100;
    int COMMON_DESCRIPTION_LENGTH = 300;
    
    //length for geo location
    int GEO_LOCATION_NAME_AND_CODE_LENGTH=50;

    int CONSUMPTION_FIELD_LIST_LENGTH = 1;

    //specical characters
    String SPECICAL_SYMBOL_REXG = "[_`~!@#$%^&*()+=|、{}':;',\\[\\].<>/?]";
    String SPECICAL_SYMBOL_REXG_MINIMAL = "[_%/]";
    char SLASH_CHAR = '/';
    String SLASH_STR = "/";
    String STANDARD_DATE_FORMAT = "yyyyMMdd";
    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    String LOCK_FOR_UPDATE = "/lock/{id}";
    String SWAGGER_LOCK_FOR_UPDATE_VALUE = "When updating the data, check if there is someone else doing the update with the data";
    String SWAGGER_LOCK_FOR_UPDATE_NOTES = "query data form by request parameter data form guid";

    String UNLOCK_FOR_UPDATE = "/unlock/{id}";
    String SWAGGER_UNLOCK_FOR_UPDATE_VALUE = "When updating the data success unlock data";
    String SWAGGER_UNLOCK_FOR_UPDATE_NOTES = "Open the locked data model based on incoming parameters";

    String UNLOCK = "/unlock/";

    // rest template
    String HTTPS = "https";
    String HTTPS_PROXY = "https_proxy";
    String HTTPS_ROOT = "http://";
    String AUTHORIZATION = "Authorization";

    // json root
    String JSON_CUSTOM_ROOT_C_COMMERICIALPRODUCTS = "ComercialProducts";
    String JSON_ROOT_C_PRODUCT = "C_Product";
    String JSON_ROOT_C_PRODUCT_TYPE = "C_ProductType";
    String JSON_CUSTOM_ROOT_C_BUSINESSCUSTOMER = "Customers";
    String JSON_ROOT_C_BUSINESSCUSTOMER = "C_BusinessPartnerCustomer";
    String JSON_ROOT_C_BUSINESSCUSTOMERTYPE = "C_BusinessPartnerCustomerType";

    static final String LOCK_PEOPLE_NAME = "JESS";

    //lock table
    String OBJECT_LOCK_TYPE = "entitlementMapping";
    String OBJECT_LOCK_TYPE_MASTER_DATA = "entitlementMasterData";
    String OBJECT_LOCK_USER_ID_KEY = "userId";

    interface LockAction //NOSONAR
    {
        String CREATE = "create";
        String UPDATE = "update";
        String COPY = "copy";
        String DELETE = "delete";
    }

    interface DataType //NOSONAR
    {
        String STRING = "String";
        String DATE = "Date";
        String DOUBLE = "Double";
        String INTEGER = "Integer";
        String BOOLEAN = "Boolean";
        String DATETIME = "DateTime";
    }

    //entitlement mapping standard attribute name
    String QUANTITY = "quantity";
    String VALID_FROM = "validFrom";
    String VALID_TO = "validTo";
    String DISTRIBUTOR_ID = "distributorId";
    String CUSTOMER_ID = "customerId";
    
    interface Operator
    {
        String EQ = "eq";
        String LIKE = "like";
    }
    
    interface ValueType
    {
        String STATIC_VALUE = "0";
        String MAPPING_FROM_INTERFACE = "1";
    }
    String ENTITLEMENT_GENERATION_INTERFACE = "Inbound_Interface_Entitlement_Generation";
    
    //fixed request name for entitlement field
    interface EntitlementField  //NOSONAR
    {
        String GUID = "guid";
        String ENTITLEMENT_TYPE_CODE = "entitlementTypeCode";
        String ENTITLEMENT_MODEL = "entitlementModel";
        String ENTITLEMENT_NO = "entitlementNo";
        //String ENTITLEMENT_MASTER_ID = "entitlementMasterId";
        //String ENTITLEMENT_MASTER_DESCRIPTION = "entitlementDescription";
        String QUANTITY = "quantity";
        String UOM = "uom";
        String VALID_FROM = "validFrom";
        String VALID_TO = "validTo";
        String STATUS = "status";
        String CUSTOMER_ID = "customerId";
        String CUSTOMER_NAME = "customerName";
        String DISTRIBUTOR_ID = "distributorId";
        String DISTRIBUTOR_NAME = "distributorName";
        String SOURCE_SYSTEM = "sourceSystem";
        String REF_DOC_TYPE = "refDocType";
        String REF_DOC_NO = "refDocNo";
        String REF_ITEM_NO = "refItemNo";
        String GENERATION_METHOD = "generationMethod";
        String REASON = "reason";
        String COMMENTS = "comments";
        String VERSION = "version";
        String CREATED_BY = "createdBy";
        String CREATED_ON = "createdOn";
        String LAST_CHANGED_BY = "lastChangedBy";
        String LAST_CHANGED_ON = "lastChangedOn";
        //DPP field 
        String CREATED_BY_ID = "createdById";
        String LAST_CHANGED_BY_ID = "lastChangedById";
        
        //add new standard field for repository
        String FOLDER_GUID = "folderGuid";
        String THE_RGIGHT = "theRight";
        String DISTRIBUTION_CHANNEL = "distributionChannel";
        String GEO_LOCATION = "geoLocation";
        String BUSINESS_CATEGORY = "businessCategory";
        String LEGACY_TRAN_DOC_NO = "legacyTrancDocNo";
        String ENTITLEMENT_MODEL_NAME_SNAPSHOT = "entitlementModelNameSnapShot";
        String CUSTOMER_NAME_SNAPSHOT = "customerNameSnapShot";
        String COMMERCIAL_PRODUCT_ID = "commercialProductId";
        String COMMERCIAL_PRODUCT_NAME_SNAPSHOT = "commercialProductNameSnapShot";
        String ENTITLEMENT_MAPPING_GUID = "entitlementMappingGuid";
        String THE_RIGHT_NAME = "theRightName";
        String DISTRIBUTION_CHANNEL_NAME = "distributionChannelName";
        String BUSINESS_CATEGORY_NAME = "businessCategoryName";
    }
}
