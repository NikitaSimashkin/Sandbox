package ru.kram.deathstar.deathstar_contract.aidl;

import ru.kram.deathstar.deathstar_contract.aidl.DeathStar;
import ru.kram.deathstar.deathstar_contract.aidl.IDeathStarResponseListener;

interface IDeathStarService {
    void fillDeathStarSync(out DeathStar deathStar);
    void fillDeathStarIn(in DeathStar deathStar);
    oneway void fillDeathStarAsync(in IDeathStarResponseListener listener);
    void inoutFillDeathStar(inout DeathStar deathStar);
}
