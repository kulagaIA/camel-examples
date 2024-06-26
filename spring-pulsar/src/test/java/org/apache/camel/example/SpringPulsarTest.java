/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.infra.pulsar.services.PulsarService;
import org.apache.camel.test.infra.pulsar.services.PulsarServiceFactory;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.apache.camel.example.pulsar.client.CamelClient.ENDPOINT_URI;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *  A unit test checking that Camel can exchange messages with Apache Pulsar.
 */
class SpringPulsarTest extends CamelSpringTestSupport {

    @RegisterExtension
    private static final PulsarService SERVICE = PulsarServiceFactory.createService();

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("/META-INF/spring/camel-server.xml");
    }

    @Test
    void should_exchange_messages() {
        template.sendBody(ENDPOINT_URI, 22);

        NotifyBuilder notify = new NotifyBuilder(context)
                .whenCompleted(1).wereSentTo("log:*").whenBodiesDone(66).create();
        assertTrue(
                notify.matches(20, TimeUnit.SECONDS), "1 message should be completed"
        );
    }
}
