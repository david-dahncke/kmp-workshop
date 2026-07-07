import Foundation
import Shared

@MainActor
class ItemDetailObserver: ObservableObject {
    @Published var state: ItemDetailState = ItemDetailStateLoading()
    private let viewModel: ItemDetailViewModel
    private let itemId: String

    init(itemId: String) {
        self.itemId = itemId
        self.viewModel = ItemDetailViewModel(
            getItemDetailUseCase: GetItemDetailUseCase(repository: DIHelper.itemRepository()),
            toggleFavoriteUseCase: ToggleFavoriteUseCase(repository: DIHelper.itemRepository())
        )
        observeState()
        viewModel.loadItem(itemId: itemId)
    }

    func toggleFavorite() { viewModel.toggleFavorite(itemId: itemId) }

    private func observeState() {
        Task {
            for await newState in viewModel.state { self.state = newState }
        }
    }
}
