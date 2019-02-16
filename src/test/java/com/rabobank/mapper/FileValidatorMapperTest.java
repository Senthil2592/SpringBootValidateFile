package com.rabobank.mapper;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rabobank.contants.AppConstants;

@RunWith(MockitoJUnitRunner.class)
public class FileValidatorMapperTest {

	@InjectMocks
	private FileValidatorMapper fileValidatorMapper;
	
	@Mock
	private Element elementMock;
	
	@Mock
	private NodeList nodeList;
	

	@Mock
	private Node node;
	
	@Test
	public void testFetchAppBeanFromXml() {
		
		when(elementMock.getElementsByTagName(isA(String.class))).thenReturn(nodeList);
		when(nodeList.item(0)).thenReturn(node);		
		when(node.getTextContent()).thenReturn(AppConstants.TEST_NUMBER);

		assertNotNull(fileValidatorMapper.fetchAppBeanFromXml(elementMock));
	}
	
	@Test
	public void testFetchAppBeanFromCsv() {
		
		String[] fieldData = {AppConstants.TEST_TEXT, AppConstants.TEST_TEXT, AppConstants.TEST_TEXT, AppConstants.TEST_NUMBER, AppConstants.TEST_NUMBER, AppConstants.TEST_NUMBER};
		assertNotNull(fileValidatorMapper.fetchAppBeanFromCsv(fieldData));

	}
}
