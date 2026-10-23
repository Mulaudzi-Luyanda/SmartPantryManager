# Manual Android verification checklist

Record Actual Result and screenshots after testing on your device; do not claim PASS until observed.

| Test | Steps | Expected | Actual |
| --- | --- | --- | --- |
| Create | Add egg, 2 pcs | Pantry shows egg 2 pcs | Not run |
| Read | Return to Pantry | Saved entry visible | Not run |
| Update | Tap egg, set 1 pcs | Pantry shows 1 pcs | Not run |
| Delete | Long-press egg, confirm | Entry removed | Not run |
| Validation | Enter empty name / quantity 0 | Error shown, no save | Not run |
| Match | Add egg 2 pcs + oil 10 ml | Scrambled Eggs suggested | Not run |
| Strict shortage | Reduce egg to 1 | Scrambled Eggs hidden | Not run |
| Singular plural | Add tomatoes 2 pcs, onion 1 pcs, oil 10 ml | Tomato Salad suggested | Not run |
| Unit conversion | Add milk 0.25 l, banana 2 pcs | Milk Banana Smoothie suggested | Not run |
| Zero suggestions | Empty pantry | Helpful empty state | Not run |
| Detail | Tap suggested recipe | Ingredients + method visible | Not run |
| Settings | Toggle expiry labels | Preference saved | Not run |
| Persistence | Close and relaunch app | Pantry survives | Not run |
| Navigation | Pantry / Recipes / Settings | All open | Not run |
