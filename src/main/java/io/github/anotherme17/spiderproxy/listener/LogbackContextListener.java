package io.github.anotherme17.spiderproxy.listener;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import io.github.anotherme17.base.common.BaseConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;


@Slf4j
public class LogbackContextListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {
    private boolean started = false;

    private static final String DEFAULT_LOG_PATH = "/var/www/logs";

    @Override
    public void start() {
        if (started) {
            return;
        }

        String serviceName;
        Properties bootStrapProps = new Properties();
        try {
            bootStrapProps.load(this.getClass().getClassLoader().getResourceAsStream("bootstrap.properties"));
            serviceName = bootStrapProps.getProperty(BaseConstants.SERVER_NAME);
            String baseLogPath = System.getProperty("log.path", DEFAULT_LOG_PATH);
            getContext().putProperty("BASE_LOG_PATH", baseLogPath);
            getContext().putProperty("SERVICE_NAME", serviceName);
        } catch (Throwable e) {
            log.error("加载bootstrap.properties异常", e);
            return;
        }
        started = true;
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public boolean isResetResistant() {
        return true;
    }

    @Override
    public void onStart(LoggerContext context) {
    }

    @Override
    public void onReset(LoggerContext context) {
    }

    @Override
    public void onStop(LoggerContext context) {
    }

    @Override
    public void onLevelChange(Logger logger, Level level) {
    }
}
