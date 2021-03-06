package org.apache.cassandra.cache;
/*
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 */


import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class AbstractCache
{
    static void registerMBean(Object cache, String table, String name)
    {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try
        {
            ObjectName mbeanName = new ObjectName("org.apache.cassandra.db:type=Caches,keyspace=" + table + ",cache=" + name);
            // unregister any previous, as this may be a replacement.
            if (mbs.isRegistered(mbeanName))
                mbs.unregisterMBean(mbeanName);
            mbs.registerMBean(cache, mbeanName);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
