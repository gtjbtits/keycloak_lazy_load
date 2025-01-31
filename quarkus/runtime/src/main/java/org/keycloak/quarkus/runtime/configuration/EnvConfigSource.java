/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.quarkus.runtime.configuration;

import static org.keycloak.quarkus.runtime.configuration.Configuration.getMappedPropertyName;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.eclipse.microprofile.config.spi.ConfigSource;

public class EnvConfigSource implements ConfigSource {

    private final Map<String, String> properties = new TreeMap<>();

    public EnvConfigSource() {
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(MicroProfileConfigProvider.NS_KEYCLOAK_PREFIX.toUpperCase().replace('.', '_'))) {
                properties.put(getMappedPropertyName(key), entry.getValue());
            }
        }
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    public String getValue(final String propertyName) {
        return System.getProperty(propertyName);
    }

    public String getName() {
        return "KcEnvVarConfigSource";
    }

    @Override
    public int getOrdinal() {
        return 350;
    }
}
