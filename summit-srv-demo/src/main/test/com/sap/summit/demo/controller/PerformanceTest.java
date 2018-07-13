
package com.sap.summit.demo.controller;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sap.summit.util.ConvertJson;

public class PerformanceTest {

  private ConvertJson json = new ConvertJson();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testMockData() throws IOException {
    String expectResult = "{\n" + "  \"data\": [\n" + "    {\n" + "      \"reason\": \"\",\n"
        + "      \"customerNameSnapShot\": \"SAP\",\n" + "      \"sourceSystem\": \"S4HCLNT500\",\n"
        + "      \"entitlementModelNameSnapShot\": \"EntitlemnetModel2\",\n"
        + "      \"refItemNo\": \"00650394330\",\n"
        + "      \"folderGuid\": \"162fe06c3f7743eab33549cceb8f5e69\",\n"
        + "      \"validFrom\": \"20180201\",\n" + "      \"createdOn\": 1527044820052,\n"
        + "      \"generationMethod\": \"Auto\",\n" + "      \"originalVersion\": 18,\n"
        + "      \"commercialProductId\": \"TG46\",\n" + "      \"customerId\": \"17100007\",\n"
        + "      \"commercialProductNameSnapShot\": \"Lenovo Laptop X260\",\n"
        + "      \"refDocNo\": \"00650394330\",\n" + "      \"validTo\": \"20181101\",\n"
        + "      \"quantity\": 1,\n" + "      \"comments\": \"\",\n"
        + "      \"distributorId\": null,\n" + "      \"refDocType\": \"\",\n"
        + "      \"entitlementNo\": 15302376,\n" + "      \"legacyTrancDocNo\": \"\",\n"
        + "      \"entitlementModel\": \"EntitlemnetModel2\",\n"
        + "      \"entitlementGuid\": \"761f2efdb59e4e7c88faef2372be29a8\",\n"
        + "      \"lastChangedOn\": 1528508577638,\n"
        + "      \"createdBy\": \"Performance1122\",\n" + "      \"geoLocation\": \"Global\",\n"
        + "      \"lastChangedBy\": \"workflow_tps1_02_01\",\n"
        + "      \"status\": \"4942e6e67da34e0991183c73dfe7ce23\"\n" + "    }\n" + "  ],\n"
        + "  \"messages\": [\n" + "    {\n"
        + "      \"message\": \"The entitlements query was successful.\",\n"
        + "      \"type\": \"S\"\n" + "    }\n" + "  ]\n" + "}";

    String resultString = json.getJsonString("classpath:example.json");

    Assert.assertEquals(resultString, expectResult);

    expectResult = "{\n" + " \"message\":\"Update Entitlement Success\",\n"
        + " \"type\":\"SUCCESS\",\n" + " \"status\":\"200\"\n" + "}";

    resultString = json.getJsonString("classpath:response.json");

    Assert.assertEquals(resultString, expectResult);

  }
}
