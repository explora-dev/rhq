/*
 *
 *  * RHQ Management Platform
 *  * Copyright (C) 2005-2012 Red Hat, Inc.
 *  * All rights reserved.
 *  *
 *  * This program is free software; you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License, version 2, as
 *  * published by the Free Software Foundation, and/or the GNU Lesser
 *  * General Public License, version 2.1, also as published by the Free
 *  * Software Foundation.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License and the GNU Lesser General Public License
 *  * for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * and the GNU Lesser General Public License along with this program;
 *  * if not, write to the Free Software Foundation, Inc.,
 *  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */

package org.rhq.server.metrics;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author John Sanda
 */
public class RawNumericMetricMapper implements ResultSetMapper<RawNumericMetric> {

    private ResultSetMapper<RawNumericMetric> mapper;

    public RawNumericMetricMapper() {
        this(false);
    }

    public RawNumericMetricMapper(boolean metaDataIncluded) {
        if (metaDataIncluded) {
            mapper = new ResultSetMapper<RawNumericMetric>() {
                @Override
                public RawNumericMetric map(ResultSet resultSet) throws SQLException {
                    RawNumericMetric rawMetric = new RawNumericMetric(resultSet.getInt(1),
                        resultSet.getDate(2).getTime(), resultSet.getDouble(3));
                    ColumnMetadata metadata = new ColumnMetadata(resultSet.getInt(4), resultSet.getLong(5));
                    rawMetric.setColumnMetadata(metadata);
                    return rawMetric;
                }
            };
        } else {
            mapper = new ResultSetMapper<RawNumericMetric>() {
                @Override
                public RawNumericMetric map(ResultSet resultSet) throws SQLException {
                    return new RawNumericMetric(resultSet.getInt(1), resultSet.getDate(2).getTime(),
                        resultSet.getDouble(3));
                }
            };
        }
    }

    @Override
    public RawNumericMetric map(ResultSet resultSet) throws SQLException {
        return mapper.map(resultSet);
    }
}
