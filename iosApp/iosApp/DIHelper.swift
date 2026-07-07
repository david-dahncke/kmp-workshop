import Shared

enum DIHelper {
    static func itemRepository() -> ItemRepository {
        return KoinComponent().get()
    }
}
