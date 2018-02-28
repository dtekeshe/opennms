/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2018 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2018 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.telemetry.listeners.sflow.proto.flows;

import java.nio.ByteBuffer;

import org.opennms.netmgt.telemetry.listeners.sflow.InvalidPacketException;

import com.google.common.base.MoreObjects;

// union address switch (address_type type) {
//    case UNKNOWN:
//      void;
//    case IP_V4:
//      ip_v4 ip;
//    case IP_V6:
//      ip_v6 ip;
// };

public class Address {
    public final AddressType type;
    public final IpV4 ipV4;
    public final IpV6 ipV6;

    public Address(final ByteBuffer buffer) throws InvalidPacketException {
        this.type = AddressType.from(buffer);
        switch (this.type) {
            case IP_V4:
                this.ipV4 = new IpV4(buffer);
                this.ipV6 = null;
                break;
            case IP_V6:
                this.ipV4 = null;
                this.ipV6 = new IpV6(buffer);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("ipV4", ipV4)
                .add("ipV6", ipV6)
                .toString();
    }
}
