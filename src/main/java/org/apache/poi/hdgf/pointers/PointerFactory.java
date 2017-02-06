/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hdgf.pointers;

import org.apache.poi.util.LittleEndian;

/**
 * Factor class to create the appropriate pointers, based on the version
 * of the file
 */
public final class PointerFactory {
    private int version;

    public PointerFactory(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public Pointer createPointer(byte[] data, int offset) {
        Pointer p;
        if (version >= 6) {
            p = new PointerV6();
            p.type = LittleEndian.getInt(data, offset);
            p.address = (int) LittleEndian.getUInt(data, offset + 4);
            p.offset = (int) LittleEndian.getUInt(data, offset + 8);
            p.length = (int) LittleEndian.getUInt(data, offset + 12);
            p.format = LittleEndian.getShort(data, offset + 16);

            return p;
        } else if (version == 5) {
            throw new RuntimeException("TODO");
        } else {
            throw new IllegalArgumentException("Visio files with versions below 5 are not supported, yours was " + version);
        }
    }
}
