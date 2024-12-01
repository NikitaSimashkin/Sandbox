package ru.kram.sandbox.common.contract_deathstar.aidl;

import ru.kram.sandbox.common.contract_deathstar.aidl.DeathStar;
import ru.kram.sandbox.common.contract_deathstar.aidl.IDeathStarResponseListener;

interface IDeathStarService {
    void fillDeathStarSync(out DeathStar deathStar);
    void fillDeathStarIn(in DeathStar deathStar);
    oneway void fillDeathStarAsync(in IDeathStarResponseListener listener);
    void inoutFillDeathStar(inout DeathStar deathStar);
}
