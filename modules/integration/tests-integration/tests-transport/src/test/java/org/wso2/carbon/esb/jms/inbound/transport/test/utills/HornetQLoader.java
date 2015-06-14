/*
*  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.esb.jms.inbound.transport.test.utills;

import org.wso2.carbon.automation.engine.context.AutomationContext;
import org.wso2.carbon.automation.engine.context.TestUserMode;
import org.wso2.carbon.automation.extensions.servers.jmsserver.controller.config.JMSBrokerConfiguration;
import org.wso2.carbon.automation.extensions.servers.jmsserver.controller.config.JMSBrokerConfigurationProvider;
import org.wso2.carbon.integration.common.utils.mgt.ServerConfigurationManager;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;
import org.wso2.esb.integration.common.utils.common.TestConfigurationProvider;

import java.io.File;

public class HornetQLoader extends ESBIntegrationTest {
	private ServerConfigurationManager serverManager = null;
	private final String HORNETQ_ALL = "hornetq-all-new.jar";
	private final String JMS_2_SPEC = "javax.jms-api-2.0.1.jar";

	public void startJMSBrokerAndConfigureESB() throws Exception {
		this.context = new AutomationContext("ESB", TestUserMode.SUPER_TENANT_ADMIN);
		this.serverManager = new ServerConfigurationManager(this.context);

		this.serverManager.copyToComponentLib(new File(
				TestConfigurationProvider.getResourceLocation() + File.separator + "artifacts" + File.separator + "ESB" + File.separator + "jar" + File.separator + HORNETQ_ALL));
		this.serverManager.copyToComponentLib(new File(
				TestConfigurationProvider.getResourceLocation() + File.separator + "artifacts" +
				File.separator + "ESB" + File.separator + "jar" + File.separator +
				JMS_2_SPEC));
		this.serverManager.applyConfiguration(new File(TestConfigurationProvider.getResourceLocation() + File.separator + "artifacts" + File.separator + "ESB" + File.separator + "jms" + File.separator + "transport" + File.separator + "axis2config" + File.separator + "activemq" + File.separator + "axis2.xml"));
	}

	public void stopJMSBrokerRevertESBConfiguration() throws Exception {
		try {
			Thread.sleep(10000L);
			if(this.serverManager != null) {
				this.serverManager.removeFromComponentLib(HORNETQ_ALL);
				this.serverManager.removeFromComponentLib(JMS_2_SPEC);
				this.serverManager.restoreToLastConfiguration();
			}
		} finally {

		}

	}

	private JMSBrokerConfiguration getJMSBrokerConfiguration() {
		return JMSBrokerConfigurationProvider.getInstance().getBrokerConfiguration();
	}
}
