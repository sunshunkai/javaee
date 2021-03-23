package com.ssk.nacos.event;

import com.alibaba.nacos.spring.context.event.config.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author ssk
 * @date 2021/3/8
 */
@Component
public class NacosEventLister {


    /**
     * After ConfigService.publishConfig()
     * @param event
     */
    @EventListener
    public void publishEvent(NacosConfigPublishedEvent event){

    }

    /**
     * After Listener.receiveConfigInfo()
     * @param event
     */
    @EventListener
    public void receivedEvent(NacosConfigReceivedEvent event){

    }

    /**
     * After configService.removeConfig()
     * @param event
     */
    @EventListener
    public void removedEvent(NacosConfigRemovedEvent event){

    }

    /**
     * ConfigService.getConfig() on timeout
     * @param event
     */
    @EventListener
    public void timeoutEvent(NacosConfigTimeoutEvent event){

    }

    /**
     * After ConfigService.addListner() or ConfigService.removeListener()
     * @param event
     */
    @EventListener
    public void listenerRegisteredEvent(NacosConfigListenerRegisteredEvent event){

    }

    /**
     * After @NacosConfigurationProperties binding
     * @param event
     */
    @EventListener
    public void propertiesBeanBoundEvent(NacosConfigurationPropertiesBeanBoundEvent event){

    }

    /**
     * After Nacos Config operations
     * @param event
     */
    @EventListener
    public void metadataEvent(NacosConfigMetadataEvent event){

    }

}
