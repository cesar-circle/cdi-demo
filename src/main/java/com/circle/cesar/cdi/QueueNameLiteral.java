/*
 * Copyright (c) 2023 Circle Internet Financial Trading Company Limited.
 * All rights reserved.
 *
 * Circle Internet Financial Trading Company Limited CONFIDENTIAL
 * This file includes unpublished proprietary source code of Circle Internet
 * Financial Trading Company Limited, Inc. The copyright notice above does not
 * evidence any actual or intended publication of such source code. Disclosure
 * of this source code or any related proprietary information is strictly
 * prohibited without the express written permission of Circle Internet Financial
 * Trading Company Limited.
 */

package com.circle.cesar.cdi;

import jakarta.enterprise.util.AnnotationLiteral;

public class QueueNameLiteral extends AnnotationLiteral<QueueName> implements QueueName {
    private final String name;

    private QueueNameLiteral(String name) {
        this.name = name;
    }

    public static QueueNameLiteral withName(String name) {
        return new QueueNameLiteral(name);
    }

    @Override
    public String value() {
        return name;
    }
}
