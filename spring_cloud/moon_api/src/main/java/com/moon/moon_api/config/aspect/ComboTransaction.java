package com.moon.moon_api.config.aspect;

import com.moon.moon_commons.constants.DbTxConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * @ClassName ComboTransaction
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/23
 * @Version V1.0
 **/
public class ComboTransaction {

    @Autowired
    private Db1TxBroker db1TxBroker;

    @Autowired
    private Db2TxBroker db2TxBroker;

    public <V> V inCombinedTx(Callable<V> callable, String[] transactions) {
        if (callable == null) {
            return null;
        }

        Callable<V> combined = Stream.of(transactions)
                .filter(ele -> !StringUtils.isEmpty(ele))
                .distinct()
                .reduce(callable, (r, tx) -> {
                    switch (tx) {
                        case DbTxConstants.DB1_TX:
                            return () -> db1TxBroker.inTransaction(r);
                        case DbTxConstants.DB2_TX:
                            return () -> db2TxBroker.inTransaction(r);
                        default:
                            return null;
                    }
                }, (r1, r2) -> r2);

        try {
            return combined.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Component
    public class Db1TxBroker {

        @Transactional(DbTxConstants.DB1_TX)
        public <V> V inTransaction(Callable<V> callable) {
            try {
                return callable.call();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Component
    public class Db2TxBroker {

        @Transactional(DbTxConstants.DB2_TX)
        public <V> V inTransaction(Callable<V> callable) {
            try {
                return callable.call();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}


