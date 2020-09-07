package com.poppo.toby.trashDaoV5;

import com.poppo.toby.trashDaoV3.ConnectionMaker;
import com.poppo.toby.trashDaoV3.NConnectionMaker;
import com.poppo.toby.trashDaoV4.TrashUserDaoV4;

public class DaoFactory {
    public TrashUserDaoV4 trashUserDaoV4() {
        return new TrashUserDaoV4(connectionMaker());
    }

    private ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }
}
