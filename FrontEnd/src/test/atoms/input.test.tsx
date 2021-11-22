import { prettyDOM } from "@testing-library/react";
import { snapshot_UNSTABLE } from "recoil"
import { InputState } from "../../recoil/atoms/InputState";

// Snapshot은 API 변경의 여지가 많아 보임

test('Input Component', async () => {
    const initialSnapshot = snapshot_UNSTABLE();
    const initialSnapshotAnswer = await initialSnapshot.getPromise(InputState);
    console.log(`State : ${initialSnapshotAnswer}`)
    expect(initialSnapshot.getLoadable(InputState).valueOrThrow()).toBe('');

    const newSnapshot = initialSnapshot.map(({ set }) => set(InputState, 'test'));
    expect(newSnapshot.getLoadable(InputState).valueOrThrow()).toBe('test');
    const newSnapshotAnswer = await newSnapshot.getPromise(InputState);
    console.log(`State : ${newSnapshotAnswer}`)
})