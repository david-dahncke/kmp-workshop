import Foundation
import Shared

@MainActor
class ItemListObserver: ObservableObject {
    @Published var state: ItemListState = ItemListStateLoading()
    private let viewModel: ItemListViewModel

    init() {
        self.viewModel = ItemListViewModel(
            getItemsUseCase: GetItemsUseCase(repository: DIHelper.itemRepository()),
            toggleFavoriteUseCase: ToggleFavoriteUseCase(repository: DIHelper.itemRepository())
        )
        observeState()
    }

    func loadItems() { viewModel.loadItems() }
    func toggleFavorite(itemId: String) { viewModel.toggleFavorite(itemId: itemId) }

    private func observeState() {
        Task {
            for await newState in viewModel.state { self.state = newState }
        }
    }
}
