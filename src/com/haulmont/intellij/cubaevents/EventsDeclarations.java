/*
 * Copyright (c) 2008-2018 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.haulmont.intellij.cubaevents;

public final class EventsDeclarations {
    public static final String EVENTLISTENER_ANNOTATIONNAME = "org.springframework.context.event.EventListener";
    public static final String APPLICATION_EVENT_CLASSNAME = "org.springframework.context.ApplicationEvent";

    public static final String EVENTS_CLASSNAME = "com.haulmont.cuba.core.global.Events";
    public static final String EVENTS_SIMPLE_CLASSNAME = "Events";
    public static final String EVENTS_PUBLISH_METHODNAME = "publish";

    private EventsDeclarations() {
    }
}